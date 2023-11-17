package majorprojectadamwetherby;

// Class for the entries in the custom hash map
public class Entry<K, V> {
    // The key for the entry
    private K key;
    // The value of the entry
    private V value;
    // The next entry
    private Entry<K, V> next;

    // Constructor for the entry
    public Entry(K key, V value, Entry<K, V> next) {
        // Set all the values to the provided values
        this.key = key;
        this.value = value;
        this.next = next;
    }

    // Get the key
    public K getKey() {
        return this.key;
    }

    // Get the value
    public V getValue() {
        return this.value;
    }

    // Get the next entry
    public Entry<K, V> getNext() {
        return this.next;
    }

    // Set the key
    public void setKey(K key) {
        this.key = key;
    }

    // Set the value
    public void setValue(V value) {
        this.value = value;
    }

    // Set the next entry
    public void setNext(Entry<K, V> next) {
        this.next = next;
    }
}
