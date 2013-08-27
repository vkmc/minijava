class Mergesort {
  int numbers;
  int helper;

  int number;

	void sort(int values) {
		this.numbers = values;
		number = values.length;
		this.helper = number;
		mergesort(0, number - 1);
  }

  private void mergesort(int low, int high) {
    // Check if low is smaller then high, if not then the array is sorted
    if (low < high) {
      // Get the index of the element which is in the middle
      int middle = low + (high - low) / 2;
      // Sort the left side of the array
      mergesort(low, middle);
      // Sort the right side of the array
      mergesort(middle + 1, high);
      // Combine them both
      merge(low, middle, high);
    }
  }

  private void merge(int low, int middle, int high) {

    // Copy both parts into the helper array
    for (int i = low; i <= high; i++) {
      helper = numbers;
    }

    int i = low;
    int j = middle + 1;
    int k = low;
    // Copy the smallest values from either the left or the right side back
    // to the original array
    while (i <= middle && j <= high) {
      if (helper <= helper) {
        numbers = helper;
        i++;
      } else {
        numbers = helper;
        j++;
      }
      k++;
    }
    // Copy the rest of the left side of the array into the target array
    while (i <= middle) {
      numbers = helper;
      k++;
      i++;
    }

  }
} 