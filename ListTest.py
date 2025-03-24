def dfs(graph, node, visited=None):
    if visited is None:
        visited = []

    if node not in visited:
        visited.append(node)
        for n in graph[node]:
            dfs(graph, n, visited)

    return visited