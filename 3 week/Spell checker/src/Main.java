// Ulyana Chaikouskaya


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> uniqueWords = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        int numberWords = scan.nextInt();
        HashMap<String, Integer> hashMap = new HashMap<>(1009);
        for (int i = 0; i < numberWords; i++){
            String word = scan.next();
            if (hashMap.get(word) != null){
                hashMap.put(word, hashMap.get(word) + 1);
            }else{
                hashMap.put(word, 1);
            }
        }

        numberWords = scan.nextInt();

        for (int i = 0; i < numberWords; i++){
            String word = scan.next();
            if (hashMap.get(word) == null){
                uniqueWords.add(word);
                hashMap.put(word, 1);
            }
        }

        System.out.println(uniqueWords.size());

        for(String temp : uniqueWords){
            System.out.println(temp);
        }

    }
}

interface Map <K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
    boolean isEmpty();
    ArrayList<KeyValueTable<K, V>> entrySet();

}

class KeyValueTable<K, V>{
    K key;
    V value;
    public KeyValueTable (K key, V value){
        this.key = key;
        this.value = value;

    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

class HashMap<K, V> implements Map<K, V>{

    List<KeyValueTable<K, V>> [] hashTable;
    int capacity;
    int mapSize;


    @Override
    public V get(K key) {
        int index = Math.abs(key.hashCode() % this.capacity);
        for(KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                return kv.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        // determine the place of our key
        int index = Math.abs(key.hashCode() % this.capacity);
        for (KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                kv.value = value;
                return;
            }
        }

        this.hashTable[index].add(
                new KeyValueTable<>(key, value)
        );
        ++this.mapSize;
    }

    @Override
    public void remove(Object key) {
        int index = Math.abs(this.hashCode() % this.capacity);
        for(KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                kv.value = null;
                break;
            }
        }

    }

    @Override
    public int size() {
        return this.mapSize;
    }

    @Override
    public boolean isEmpty() {
        return (this.mapSize == 0);
    }

    @Override
    public ArrayList<KeyValueTable<K, V>> entrySet() {
        ArrayList<KeyValueTable<K, V>> entrySet = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (this.hashTable[i] != null) {
                List<KeyValueTable<K, V>> linkedList = this.hashTable[i];
                for (KeyValueTable<K, V> kv : linkedList) {
                    entrySet.add(kv);
                }
            }
        }
        return entrySet;
    }

    public HashMap(int capacity){
        this.capacity = capacity;
        this.mapSize = 0;
        hashTable = new List[capacity];
        for (int i = 0; i < this.capacity; ++i){
            this.hashTable[i] = new LinkedList<>();
        }

    }

    public List<KeyValueTable<K, V>>[] getHashTable() {
        return this.hashTable;
    }




}