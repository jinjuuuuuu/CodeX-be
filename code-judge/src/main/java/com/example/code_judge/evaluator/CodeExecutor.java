package com.example.code_judge.evaluator;

import java.io.*;
import java.nio.file.Files;
// import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CodeExecutor {
    // 제출된 코드 실행
    public String execute(String code, String input, String language) {
        String fileName = null;
        String output = "";
        try {
            // JSON 문자열을 공백으로 구분된 입력값으로 변환
            input = parseInput(input);

            // 제출된 코드를 파일로 저장
            fileName = saveCodeToFile(code, language);

            // 언어별 컴파일 및 실행 명령 설정
            ProcessBuilder processBuilder;
            if (language.equalsIgnoreCase("java")) {
                processBuilder = new ProcessBuilder("javac", fileName); // Java 컴파일
                processBuilder.redirectErrorStream(true);
                Process compileProcess = processBuilder.start();
                int compileExitCode = compileProcess.waitFor();
                if (compileExitCode != 0) {
                    return captureError(compileProcess); // 컴파일 오류 반환
                }
                // Java 실행
                processBuilder = new ProcessBuilder("java", "-cp", "temp", "SubmittedCode");
            } else if (language.equalsIgnoreCase("python")) {
                processBuilder = new ProcessBuilder("python3", fileName); // Python 실행
            } else if (language.equalsIgnoreCase("c")) {
                String executableName = "SubmittedCode_" + System.currentTimeMillis();
                processBuilder = new ProcessBuilder("gcc", fileName, "-o", executableName); // C 컴파일
                processBuilder.redirectErrorStream(true);
                Process compileProcess = processBuilder.start();
                int compileExitCode = compileProcess.waitFor();
                if (compileExitCode != 0) {
                    return captureError(compileProcess); // 컴파일 오류 반환
                }
                processBuilder = new ProcessBuilder("./" + executableName); // 실행 파일 실행
            } else if (language.equalsIgnoreCase("cpp")) {
                String executableName = "SubmittedCode_" + System.currentTimeMillis();
                processBuilder = new ProcessBuilder("g++", fileName, "-o", executableName); // C++ 컴파일
                processBuilder.redirectErrorStream(true);
                Process compileProcess = processBuilder.start();
                int compileExitCode = compileProcess.waitFor();
                if (compileExitCode != 0) {
                    return captureError(compileProcess); // 컴파일 오류 반환
                }
                processBuilder = new ProcessBuilder("./" + executableName); // 실행 파일 실행
            } else {
                throw new IllegalArgumentException("지원하지 않는 언어입니다: " + language);
            }

            // 실행 프로세스 시작
            processBuilder.redirectErrorStream(true);
            Process runProcess = processBuilder.start();
            if (input != null && !input.isEmpty()) {
                try (OutputStream outputStream = runProcess.getOutputStream()) {
                    outputStream.write(input.getBytes());
                    outputStream.flush();
                }
            }

            // 실행 결과 캡처
            output = captureOutput(runProcess);
            runProcess.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage(); // 예외 발생 시 오류 메시지 반환
        } finally {
            // 리소스 정리
            if (fileName != null) {
                deleteFile(fileName);
                deleteFile(fileName.replace(".java", ".class")); // Java 클래스 파일 삭제
            }
        }
        return output.trim();
    }

    // JSON 문자열을 공백으로 구분된 입력값으로 변환
    private String parseInput(String input) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] inputs = objectMapper.readValue(input, String[].class);
        StringBuilder parsedInput = new StringBuilder();
        for (String str : inputs) {
            parsedInput.append(str).append(" ");
        }
        return parsedInput.toString().trim();
    }

    // 제출된 코드를 파일로 저장
    private String saveCodeToFile(String code, String language) throws Exception {
        String directory = "temp";
        Files.createDirectories(Paths.get(directory)); // temp 디렉터리 생성
        String fileName;
        if (language.equalsIgnoreCase("java")) {
            fileName = directory + "/SubmittedCode.java";
        } else if (language.equalsIgnoreCase("python")) {
            fileName = directory + "/SubmittedCode.py";
        } else if (language.equalsIgnoreCase("c")) {
            fileName = directory + "/SubmittedCode.c";
        } else if (language.equalsIgnoreCase("cpp")) {
            fileName = directory + "/SubmittedCode.cpp";
        } else {
            throw new IllegalArgumentException("지원하지 않는 언어입니다: " + language);
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(code);
        }
        return fileName;
    }

    // 실행 결과 캡처
    private String captureOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString();
        }
    }

    // 실행 또는 컴파일 오류 캡처
    private String captureError(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            StringBuilder error = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                error.append(line).append("\n");
            }
            return "Error: " + error.toString();
        }
    }

    // 파일 삭제
    private void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("파일 삭제 실패: " + fileName);
        }
    }
}