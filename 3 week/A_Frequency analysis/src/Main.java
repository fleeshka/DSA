// Ulyana Chaikouskaya


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numberWords = scan.nextInt();
        HashMap<String, Integer> hashMap = new HashMap<>(101);
        for (int i = 0; i < numberWords; i++){
            String word = scan.next();
            if (hashMap.get(word) != null){
                hashMap.put(word, hashMap.get(word) + 1);
            }else{
                hashMap.put(word, 1);
            }
        }
        SortMap(hashMap.entrySet());
    }

    private static void  SortMap (ArrayList<KeyValueTable<String, Integer>> entrySet) {
        for (int i = 1; i < entrySet.size(); i++) {
            KeyValueTable<String, Integer> currentElement = entrySet.get(i);
            int j = i - 1;
            while (j >= 0 &&
                    (currentElement.getValue() > entrySet.get(j).getValue() ||
                            (currentElement.getValue().equals(entrySet.get(j).getValue()) &&
                                    currentElement.getKey().compareTo(entrySet.get(j).getKey()) < 0))) {

                entrySet.set(j + 1, entrySet.get(j));
                j--;
            }
            entrySet.set(j + 1, currentElement);
        }
        for (int i = 0; i < entrySet.size(); i++) {
            System.out.println(entrySet.get(i).getKey() + " " + entrySet.get(i).getValue());
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