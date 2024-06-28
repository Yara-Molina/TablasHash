package models;

import java.util.LinkedList;

public class HashTable<K, V> {
    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private int hashFunction;

    public HashTable(int initialCapacity, int hashFunction) {
        this.size = 0;
        this.hashFunction = hashFunction;
        table = new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash1(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private int hash2(K key) {
        return Math.abs((key.hashCode() / table.length) % table.length);
    }

    private int getHash(K key) {
        return hashFunction == 1 ? hash1(key) : hash2(key);
    }

    public void put(K key, V value) {
        if ((size / table.length) > 0.7) {
            rehash();
        }
        int index = getHash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = getHash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private void rehash() {
        LinkedList<Entry<K, V>>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
        for (LinkedList<Entry<K, V>> bucket : oldTable) {
            for (Entry<K, V> entry : bucket) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }
}
