package com.cybernetic;

import java.util.ArrayList;
import java.util.Iterator;

public class OrganInventory {

    ArrayList<CyberneticOrgan> organs = new ArrayList<>();
    int maxCapacity = 1000; // default: 1000

    /**
     * This method adds new organ
     * @param organ of type CyberneticOrgan
     */
    public void addOrgan(CyberneticOrgan organ) {

        // Validation for - Each organ must have unique ID.
        Iterator<CyberneticOrgan> iterator = organs.iterator();
        while (iterator.hasNext()) {
            if (organ.getId().equals(iterator.next().getId())) {
                throw new IllegalArgumentException("Organ ID already exists.");
            }
        }

        organs.add(organ);
    }

    /**
     * This method deletes an organ
     * @param organ of type CyberneticOrgan
     */
    public void removeOrgan(CyberneticOrgan organ, String reason) throws Exception {

        boolean found = false;

        if (reason == null || reason.isEmpty()) {
            throw new Exception("Removal reason is not given.");
        }

        Iterator<CyberneticOrgan> iterator = organs.iterator();
        while (iterator.hasNext()) {
            if (organ.getId().equals(iterator.next().getId())) {
                iterator.remove(); // Safely remove the current element
                found = true;
            }
        }
        if (!found) {
            throw new Exception("Organ not found.");
        }
    }

    /**
     * This method sorts by Power Level descending.
     * @return sorted Organs
     */
    public ArrayList<CyberneticOrgan> sortByPowerLevel() {

        return quickSortByPowerLevel(organs);
    }

    /**
     * This method sorts by manufactureDate (newest first).
     * @return sorted Organs
     */
    public ArrayList<CyberneticOrgan> sortByManufactureDate() {

        return mergeSortManufactureDate(organs);
    }

    /**
     * This method sorts by compatibilityScore (highest first).
     * @return sorted Organs
     */
    public ArrayList<CyberneticOrgan> sortByCompatibilityScore() {

        return bubbleSort(organs);
    }

    /**
     * This method uses quickSort and sorts by Power Level descending.
     * @param list
     * @return
     */
    private ArrayList<CyberneticOrgan> quickSortByPowerLevel(ArrayList<CyberneticOrgan> list) {

        if (list == null || list.size() <= 1) {
            return new ArrayList<>(list); // Return a copy of the list if it's empty or has one element
        }

        ArrayList<CyberneticOrgan> lessLevel = new ArrayList<>();
        ArrayList<CyberneticOrgan> equalLevel = new ArrayList<>();
        ArrayList<CyberneticOrgan> greaterLevel = new ArrayList<>();

        CyberneticOrgan pivot = list.get(0);

        Iterator<CyberneticOrgan> iterator = list.iterator();
        while (iterator.hasNext()) {
            CyberneticOrgan element = iterator.next();
            if (element.getPowerLevel() < pivot.getPowerLevel()) {
                lessLevel.add(element);
            } else if (element.getPowerLevel() == pivot.getPowerLevel()) {
                equalLevel.add(element);
            } else {
                greaterLevel.add(element);
            }
        }
        ArrayList<CyberneticOrgan> sortedList = new ArrayList<>();
        sortedList.addAll(quickSortByPowerLevel(lessLevel));
        sortedList.addAll(equalLevel);
        sortedList.addAll(quickSortByPowerLevel(greaterLevel));
        return sortedList;
    }

    /**
     * This method uses mergesort and sorts by manufactureDate (newest first).
     * @param list
     * @return
     */
    private ArrayList<CyberneticOrgan> mergeSortManufactureDate(ArrayList<CyberneticOrgan> list) {

        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        ArrayList<CyberneticOrgan> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<CyberneticOrgan> right = new ArrayList<>(list.subList(mid, list.size()));

        left = mergeSortManufactureDate(left);
        right = mergeSortManufactureDate(right);

        return mergeManufactureDate(left, right);
    }

    /**
     * This method merges the given organs ArrayList.
     * @param left
     * @param right
     * @return merged list
     */
    private ArrayList<CyberneticOrgan> mergeManufactureDate(ArrayList<CyberneticOrgan> left, ArrayList<CyberneticOrgan> right) {
        ArrayList<CyberneticOrgan> sortedList = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            // if (left.get(i).compareTo(right.get(j)) <= 0) {
            if (left.get(i).getManufactureDate().isBefore(right.get(j).getManufactureDate())) {
                sortedList.add(left.get(i));
                i++;
            } else {
                sortedList.add(right.get(j));
                j++;
            }
        }

        sortedList.addAll(left.subList(i, left.size()));
        sortedList.addAll(right.subList(j, right.size()));

        return sortedList;
    }

    /**
     * This method uses bubblesort and sorts by compatibilityScore (highest first).
     * @param list
     * @return
     */
    private ArrayList<CyberneticOrgan> bubbleSort(ArrayList<CyberneticOrgan> list) {

        ArrayList<CyberneticOrgan> sortedList = new ArrayList<>(list);

        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = 0; j < sortedList.size() - i - 1; j++) {
                // if (sortedList.get(j).compareTo(sortedList.get(j + 1)) > 0) {
                if (sortedList.get(j).getCompatibilityScore() > sortedList.get(j + 1).getCompatibilityScore()) {
                    // Swap elements
                    CyberneticOrgan temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }
        return sortedList;
    }
}
