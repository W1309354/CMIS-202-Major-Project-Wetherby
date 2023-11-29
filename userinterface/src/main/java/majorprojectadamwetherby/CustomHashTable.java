package majorprojectadamwetherby;

import java.util.ArrayList;
import java.util.Objects;

// Node for the hash table
class HashNode<K, V> {
    // Key of the node
    private K key;
    // Value of the node
    private V value;
    // Hash code of the node
    private final int hashCode;

    // Reference to next node
    HashNode<K, V> next;

    // Constructor
    public HashNode(K key, V value, int hashCode) {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }

    // Get the key
    public K getKey() {
        return this.key;
    }

    // Get the value
    public V getValue() {
        return this.value;
    }

    // Get the hash code
    public int hashCode() {
        return this.hashCode;
    }

    // Get the next node
    public HashNode<K, V> getNext() {
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

    // Set the next node
    public void setNext(HashNode<K, V> next) {
        this.next = next;
    }
}


// The custom implementation hash table class
public class CustomHashTable<K, V> {
    // Bucket array stores an array of chains
    // Can use the previous HashNode class from the HashMap
    private ArrayList<HashNode<K, V>> bucketArray;

    // The capacity of the array list
    private int numBuckets;

    // The current size of the array list
    private int size;

    // Constructor
    public CustomHashTable() {
        // Initialize the capacity and size
        bucketArray = new ArrayList<>();
        this.numBuckets = 10;
        this.size = 0;

        // Create empty chains
        for (int i = 0; i < numBuckets; i++) {
            bucketArray.add(null);
        }
    }

    // Get the size
    public int getSize() {
        return size;
    }

    // Check if the array is empty
    public boolean isEmpty() {
        return getSize() == 0;
    }

    // Get the hash code for a key
    private final int hashCode(K key) {
        return Objects.hashCode(key);
    }

    // Hash function to find the index for a key
    private int getBucketIndex(K key) {
        int hashCode = hashCode(key);
        int index = hashCode % numBuckets;
        // key.hashCode() should be negative
        index = index < 0 ? index * -1 : index;
        return index;
    }

    // Adds a key and value to the hash
    public void add(K key, V value) {
        // Find the head of the chain for the key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        // Check if the key already exists
        while (head != null) {
            if (head.getKey().equals(key) && hashCode == head.hashCode()) {
                head.setValue(value);
                return;
            }

            head = head.getNext();
        }

        // Increment the size
        size++;
        // Insert the key in the chain
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value, hashCode);
        newNode.setNext(head);
        bucketArray.set(bucketIndex, newNode);

        // If the load factor is too much then double the table size
        if ((1.0 * size) / numBuckets >= 0.7) {
            ArrayList<HashNode<K, V>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++) {
                bucketArray.add(null);
            }

            for (HashNode<K, V> headNode : temp) {
                while (headNode != null) {
                    add(headNode.getKey(), headNode.getValue());
                    headNode = headNode.getNext();
                }
            }
        }
    }

    // Returns a value for a key
    public V get(K key) {
        // Find the head of the chain for the key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        // Search for the key in the chain
        while (head != null) {
            // Check if the HashNode has the key
            if (head.getKey().equals(key) && head.hashCode() == hashCode) {
                // If so return that value
                return head.getValue();
            }

            // Otherwise continue
            head = head.getNext();
        }

        // If the key wasn't found return null
        return null;
    }

    // Remove a given key
    public V remove(K key) {
        // Apply hash function to find index for the key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        // Get the head of the chain
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        // Search for the key in its chain
        HashNode<K, V> prev = null;
        while (head != null) {
            // Check if the key was found
            if (head.getKey().equals(key) && hashCode == head.hashCode()) {
                // If so break the loop
                break;
            }

            // Otherwise keep moving through the chain
            prev = head;
            head = head.getNext();
        }

        // Make sure the key was or wasn't found
        if (head == null) {
            return null;
        }

        // Reduce the size
        size--;

        // Remove the key
        if (prev != null) {
            prev.setNext(head.getNext());
        } else {
            bucketArray.set(bucketIndex, head.getNext());
        }

        return head.getValue();
    }
}
