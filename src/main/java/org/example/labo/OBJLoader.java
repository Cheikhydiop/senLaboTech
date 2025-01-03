package org.example.labo;
// OBJLoader.java

import javafx.scene.Group;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.MeshView;
import javafx.geometry.Point3D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {
    private List<Point3D> vertices = new ArrayList<>();
    private List<Point3D> normals = new ArrayList<>();
    private List<Point3D> textureCoords = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();

    private static class Face {
        int[] vertexIndices;
        int[] normalIndices;
        int[] textureIndices;

        Face(int[] vertexIndices, int[] textureIndices, int[] normalIndices) {
            this.vertexIndices = vertexIndices;
            this.textureIndices = textureIndices;
            this.normalIndices = normalIndices;
        }
    }

    public Group load(String filePath) throws IOException {
        Group group = new Group();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                switch (parts[0]) {
                    case "v":  // Vertex
                        vertices.add(new Point3D(
                                Double.parseDouble(parts[1]),
                                Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3])
                        ));
                        break;
                    case "vn": // Normal
                        normals.add(new Point3D(
                                Double.parseDouble(parts[1]),
                                Double.parseDouble(parts[2]),
                                Double.parseDouble(parts[3])
                        ));
                        break;
                    case "vt": // Texture coordinate
                        textureCoords.add(new Point3D(
                                Double.parseDouble(parts[1]),
                                parts.length > 2 ? Double.parseDouble(parts[2]) : 0,
                                0
                        ));
                        break;
                    case "f":  // Face
                        processFace(parts);
                        break;
                }
            }
        }

        // Create mesh from parsed data
        TriangleMesh mesh = new TriangleMesh();

        // Add vertices to mesh
        for (Point3D vertex : vertices) {
            mesh.getPoints().addAll((float) vertex.getX(), (float) vertex.getY(), (float) vertex.getZ());
        }

        // Add texture coordinates (even if not used)
        mesh.getTexCoords().addAll(0, 0);

        // Add faces to mesh
        for (Face face : faces) {
            for (int i = 0; i < face.vertexIndices.length; i++) {
                mesh.getFaces().addAll(
                        face.vertexIndices[i] - 1,    // vertex index (1-based to 0-based)
                        0                             // texture coordinate index
                );
            }
        }

        MeshView meshView = new MeshView(mesh);
        group.getChildren().add(meshView);

        return group;
    }

    private void processFace(String[] parts) {
        int[] vertexIndices = new int[parts.length - 1];
        int[] textureIndices = new int[parts.length - 1];
        int[] normalIndices = new int[parts.length - 1];

        for (int i = 1; i < parts.length; i++) {
            String[] indices = parts[i].split("/");
            vertexIndices[i - 1] = Integer.parseInt(indices[0]);
            if (indices.length > 1 && !indices[1].isEmpty()) {
                textureIndices[i - 1] = Integer.parseInt(indices[1]);
            }
            if (indices.length > 2) {
                normalIndices[i - 1] = Integer.parseInt(indices[2]);
            }
        }

        faces.add(new Face(vertexIndices, textureIndices, normalIndices));
    }
}

// Question.java
