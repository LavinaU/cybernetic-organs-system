package com.cybernetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;  // <-- Import the Comparator class
import java.util.List;

public class OrganInventory {
    private final List<Organ> inventory;

    public OrganInventory() {
        this.inventory = new ArrayList<>();
    }

    public void addOrgan(Organ organ) {
        inventory.add(organ);
    }

    public List<Organ> getOrganList() {
        return Collections.unmodifiableList(inventory);
    }

    //ability to sort by multiple properties in order. name, model, compatibility using built-in sort
    public List<Organ> sortOrganByNameModelAndCompatibilityUsingBuiltInSort() {
        // compartator to sort by name, model, and compatibility in order
        Comparator<Organ> organComparator = Comparator
                .comparing(Organ::getName)        // Compare by name
                .thenComparing(Organ::getModel)   // Then by model
                .thenComparing(Organ::getCompatibility);  // Finally by compatibility

        // uses Collections.sort (Java’s built-in sorting functionality) to sort the inventory list
        Collections.sort(inventory, organComparator);
        return inventory;
    }

    //ability to sort by multiple properties in order. name, model, compatibility using quicksort
    public List<Organ> quickSortOrganByNameModelAndCompatibility(List<Organ> unmodifiableOrganList) {
        // create a copy of the list
        List<Organ> organList = new ArrayList<>(unmodifiableOrganList);
        quickSort(organList, 0, organList.size() - 1);
        return organList;
    }

    private void quickSort(List<Organ> organList, int low, int high) { // sorts the organ inventory by name, model, and compatibility
        if (low < high) {
            int pi = partition(organList, low, high);  // partition index
            quickSort(organList, low, pi - 1);  //  sort the left part recursively
            quickSort(organList, pi + 1, high); // sort the right part recursively
        }
    }

    private int partition(List<Organ> organList, int low, int high) {
        Organ pivot = organList.get(high); // choose the last element as the pivot
        int i = low - 1; // smaller num index

        for (int j = low; j < high; j++) {
            if (compareOrgans(organList.get(j), pivot) <= 0) {
                i++;
                swap(organList, i, j);  // Swap if the element is smaller or equal to pivot
            }
        }
        swap(organList, i + 1, high);  // Swap the pivot with the element at i + 1
        return i + 1;
    }

    private void swap(List<Organ> organList, int i, int j) {
        Organ temp = organList.get(i);
        organList.set(i, organList.get(j));
        organList.set(j, temp);
    }

    private int compareOrgans(Organ o1, Organ o2) {
        int nameComparison = o1.getName().compareTo(o2.getName());
        if (nameComparison != 0) return nameComparison;

        int modelComparison = o1.getModel().compareTo(o2.getModel());
        if (modelComparison != 0) return modelComparison;

        return o1.getCompatibility().compareTo(o2.getCompatibility());
    }

}