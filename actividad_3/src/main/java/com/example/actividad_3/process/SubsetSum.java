package com.example.actividad_3.process;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubsetSum {

    // ¿Existe algún subconjunto que sume target?
    public boolean existsSubset(int[] nums, int target) {
        return backtrackExists(nums, target, 0);
    }

    private boolean backtrackExists(int[] nums, int remaining, int idx) {
        if (remaining == 0) return true;            // caso éxito
        if (idx == nums.length) return false;       // sin elementos y no llegamos

        // Elegir nums[idx]
        if (backtrackExists(nums, remaining - nums[idx], idx + 1)) return true;

        // No elegir nums[idx]
        return backtrackExists(nums, remaining, idx + 1);
    }

    // Devuelve un subconjunto (valores) que suma target (o lista vacía si no hay)
    public List<Integer> findOneSubset(int[] nums, int target) {
        List<Integer> path = new ArrayList<>();
        if (collect(nums, target, 0, path)) return path;
        return Collections.emptyList();
    }

    private boolean collect(int[] nums, int remaining, int idx, List<Integer> path) {
        if (remaining == 0) return true;
        if (idx == nums.length) return false;

        // Incluir nums[idx]
        path.add(nums[idx]);
        if (collect(nums, remaining - nums[idx], idx + 1, path)) return true;

        // Backtrack (deshacer la inclusión)
        path.remove(path.size() - 1);

        // Excluir nums[idx]
        return collect(nums, remaining, idx + 1, path);
    }
}

    

