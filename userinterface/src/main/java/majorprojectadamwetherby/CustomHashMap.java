package majorprojectadamwetherby;

// Class for my custom hash map implementation
public class CustomHashMap<K, V> {
    // The default capacity for the hash map
    private static final int DEFAULT_CAPACITY = 16;

    // The capacity of the hash map
    private int capacity;
    // The size of the hash map
    private int size;
    // The entries in the hash map
    private Entry<K, V>[] table;

    // Empty constructor for the custom hash map
    public CustomHashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new Entry[this.capacity];
    }

    // Constructor for capacity
    public CustomHashMap(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
    }

    // Hash a key
    private int hash(K key) {
        return key.hashCode() % this.capacity;
    }

    // Put a key and value into the custom hash map
    public void put(K key, V value) {
        // Get the index
        int index = this.hash(key);

        // Create an entry
        Entry<K, V> newEntry = new Entry(key, value, null);

        // Check if the entry doesn't exist
        if (this.table[index] == null) {
            // Set the index if it doesn't exist
            this.table[index] = newEntry;
        } else {
            // Initialize the previous entry variable
            Entry<K, V> previousEntry = null;
            // Get the current entry
            Entry<K, V> currentEntry = this.table[index];

            // Loop until the entry is null
            while (currentEntry != null) {
                // Check if the entry's key is the same as the provided key
                if (currentEntry.getKey().equals(key)) {
                    // If so set the value of the entry
                    currentEntry.setValue(value);
                    // Break
                    break;
                }

                // Set the previous entry to the current entry
                previousEntry = currentEntry;
                // Get the next entry
                currentEntry = currentEntry.getNext();
            }

            // Make sure the previous entry isn't null
            if (previousEntry != null) {
                // Set the next entry of the previous entry
                previousEntry.setNext(newEntry);
            }
        }
    }

    // Get the value from a key
    public V get(K key) {
        // Initialize the value
        V value = null;

        // Get the index
        int index = this.hash(key);

        // Get the current entry
        Entry<K, V> entry = this.table[index];

        // Loop until the entry is null
        while (entry != null) {
            // Check if the key is the same as the key provided
            if (entry.getKey().equals(key)) {
                // Get the value of that entry
                value = entry.getValue();
                // Break the loop
                break;
            }

            // Get the next entry
            entry = entry.getNext();
        }

        // Return the value
        return value;
    }

    // Set a key's value
    public void set(K key, V value) {
        // Get the index
        int index = this.hash(key);

        // Get the current entry
        Entry<K, V> entry = this.table[index];

        // Loop until the entry is null
        while (entry != null) {
            // Check if the key is the same as the key provided
            if (entry.getKey().equals(key)) {
                // Set the value of the entry
                entry.setValue(value);
                // Break the loop
                break;
            }

            // Get the next entry
            entry = entry.getNext();
        }
    }

    // Remove a key
    public void remove(K key) {
        // Get the index
        int index = this.hash(key);

        // Get the previous entry
        Entry previousEntry = null;
        // Get the current entry
        Entry currentEntry = this.table[index];

        // Loop until the entry is null
        while (currentEntry != null) {
            // Check if the entry equals the key provided
            if (currentEntry.getKey().equals(key)) {
                // Check if the previous entry is null
                if (previousEntry == null) {
                    // If so then get the next entry
                    currentEntry = currentEntry.getNext();
                    // Set the new current entry with its' index
                    this.table[index] = currentEntry;
                    // Return
                    return;
                } else {
                    // Otherwise set the previous entry's next entry to the current entry's next
                    // entry
                    previousEntry.setNext(currentEntry.getNext());
                    return;
                }
            }

            // Set the previous entry to the current entry
            previousEntry = currentEntry;
            // Set the current entry to the next entry
            currentEntry = currentEntry.getNext();
        }
    }

    // Turn the custom hash map to a string
    public String toString() {
        // Initialize the string
        String toReturn = "";

        // Loop through the hash map
        for (int i = 0; i < this.capacity; i++) {
            // Check if the entry is not null
            if (this.table[i] == null) {
                continue;
            }

            // Get the current entry
            Entry<K, V> currentEntry = this.table[i];
            // Loop until the current entry is null
            while (currentEntry != null) {
                // Add to the to return string with the key and value of the entry
                toReturn += "\n"
                        + String.format("Key is %s and value is %s", currentEntry.getKey(), currentEntry.getValue());
                // Set the next entry
                currentEntry = currentEntry.getNext();
            }
        }

        // Return the string
        return toReturn;
    }
}