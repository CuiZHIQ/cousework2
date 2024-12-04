// Compare two cards and return comparison result as ArrayList<Integer>
ArrayList<Integer> cardCompare(String card1, String card2) {
    ArrayList<Integer> result = new ArrayList<>();
    
    // Extract suit and number
    char suit1 = card1.charAt(card1.length() - 1);
    char suit2 = card2.charAt(card2.length() - 1);
    int num1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
    int num2 = Integer.parseInt(card2.substring(0, card2.length() - 1));
    
    // Compare suits first (H < C < D < S)
    String suits = "HCDS";
    int suitCompare = Integer.compare(suits.indexOf(suit1), suits.indexOf(suit2));
    
    if (suitCompare != 0) {
        result.add(suitCompare < 0 ? -1 : 1);
    } else {
        result.add(Integer.compare(num1, num2));
    }
    return result;
}

// Bubble sort implementation returning ArrayList<Integer>
ArrayList<Integer> bubbleSort(ArrayList<String> array) {
    ArrayList<Integer> indices = new ArrayList<>();
    for (int i = 0; i < array.size(); i++) {
        indices.add(i);
    }
    
    for (int i = 0; i < array.size() - 1; i++) {
        for (int j = 0; j < array.size() - i - 1; j++) {
            if (cardCompare(array.get(indices.get(j)), 
                array.get(indices.get(j + 1))).get(0) > 0) {
                // Swap indices
                int temp = indices.get(j);
                indices.set(j, indices.get(j + 1));
                indices.set(j + 1, temp);
            }
        }
    }
    return indices;
}

// Merge sort implementation returning ArrayList<Integer>
ArrayList<Integer> mergeSort(ArrayList<String> array) {
    ArrayList<Integer> indices = new ArrayList<>();
    for (int i = 0; i < array.size(); i++) {
        indices.add(i);
    }
    
    mergeSortHelper(array, indices, 0, array.size() - 1);
    return indices;
}

private void mergeSortHelper(ArrayList<String> array, ArrayList<Integer> indices, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSortHelper(array, indices, left, mid);
        mergeSortHelper(array, indices, mid + 1, right);
        merge(array, indices, left, mid, right);
    }
}

private void merge(ArrayList<String> array, ArrayList<Integer> indices, int left, int mid, int right) {
    ArrayList<Integer> temp = new ArrayList<>();
    int i = left, j = mid + 1;
    
    while (i <= mid && j <= right) {
        if (cardCompare(array.get(indices.get(i)), array.get(indices.get(j))).get(0) <= 0) {
            temp.add(indices.get(i++));
        } else {
            temp.add(indices.get(j++));
        }
    }
    
    while (i <= mid) temp.add(indices.get(i++));
    while (j <= right) temp.add(indices.get(j++));
    
    for (i = 0; i < temp.size(); i++) {
        indices.set(left + i, temp.get(i));
    }
}

// Measure bubble sort time
long measureBubbleSort(String filename) throws IOException {
    ArrayList<String> cards = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(
        new FileReader("D:\\Code\\" + filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            cards.add(line.trim());
        }
    }
    
    long startTime = System.currentTimeMillis();
    bubbleSort(cards);
    return System.currentTimeMillis() - startTime;
}

// Measure merge sort time
long measureMergeSort(String filename) throws IOException {
    ArrayList<String> cards = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(
        new FileReader("D:\\Code\\" + filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            cards.add(line.trim());
        }
    }
    
    long startTime = System.currentTimeMillis();
    mergeSort(cards);
    return System.currentTimeMillis() - startTime;
}

// Generate CSV comparison file
void sortComparison(String[] filenames) throws IOException {
    try (FileWriter writer = new FileWriter("sortComparison.csv")) {
        // Write header
        writer.write(",");
        for (String filename : filenames) {
            int size = Integer.parseInt(filename.substring(4, filename.indexOf(".")));
            writer.write(size + ",");
        }
        writer.write("\n");
        
        // Write bubble sort results
        writer.write("bubbleSort,");
        for (String filename : filenames) {
            writer.write(measureBubbleSort(filename) + ",");
        }
        writer.write("\n");
        
        // Write merge sort results
        writer.write("mergeSort,");
        for (String filename : filenames) {
            writer.write(measureMergeSort(filename) + ",");
        }
        writer.write("\n");
    }
}