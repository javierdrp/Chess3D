package rendering.renderer;

import rendering.mesh.Mesh3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeshBuffer {

    private List<Mesh3D> meshes;

    public MeshBuffer(Mesh3D... meshes) {
        this.meshes = new ArrayList<>();
        this.meshes.addAll(Arrays.asList(meshes));
    }

    public void clear() {
        meshes = new ArrayList<>();
    }

    public void addMesh(Mesh3D mesh) {
        meshes.add(mesh);
    }

    public List<Mesh3D> getMeshes() {
        return meshes;
    }
}
