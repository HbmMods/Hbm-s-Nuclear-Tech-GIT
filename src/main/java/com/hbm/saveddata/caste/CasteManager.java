package com.hbm.saveddata.caste;

import java.util.*;

public class CasteManager {
    private final List<Caste> castes;

    public CasteManager() {
        this.castes = new ArrayList<>();
    }

    public List<Caste> getCastes() {
        return castes;
    }

    public Caste createCaste(String name) {
        Caste newCaste = new Caste(name);
        this.castes.add(newCaste);
        return newCaste;
    }

    public void deleteCaste(UUID casteId) {
        this.castes.removeIf(caste -> caste.getId().equals(casteId));
    }
}