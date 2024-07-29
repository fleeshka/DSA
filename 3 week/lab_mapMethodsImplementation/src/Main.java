import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        HashMap<String, Integer> hashMap = new HashMap<>(11);
        System.out.println(hashMap.size());
        System.out.println(hashMap.isEmpty());



    }
}

interface Map <K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
    boolean isEmpty();


}


class KeyValueTable<K, V>{
    K key;
    V value;
    public KeyValueTable (K key, V value){
        this.key = key;
        this.value = value;

    }
}

class HashMap<K, V> implements Map<K, V>{

    List<KeyValueTable<K, V>> [] hashTable;
    int capacity;
    int mapSize;



    @Override
    public V get(K key) {
        int index = this.hashCode() % this.capacity;
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
        int index = key.hashCode() % this.capacity;
        for (KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                //if we find this index --> rewrite it
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
        int index = this.hashCode() % this.capacity;
        for(KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                //if we find this index --> overwrite it
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

    public HashMap(int capacity){
        this.capacity = capacity;
        this.mapSize = 0;
        hashTable = new List[capacity];
        for (int i = 0; i < this.capacity; ++i){
            this.hashTable[i] = new LinkedList<>();
        }

    }

}