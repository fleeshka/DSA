// Ulyana Chaikouskaya
// rely on code exercise from week 4


import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);


        int commandNumber = Integer.parseInt(scan.nextLine());
        HashMap<String, String> hashMap = new HashMap<>(1001);

        StringBuilder output = new StringBuilder();
        String fullCommandLine;
        String[] splitCommand;
        fullCommandLine = scan.nextLine();

        for (int i = 0; i < commandNumber; i++) {
            splitCommand = fullCommandLine.split(" ", 2);
            String command = splitCommand[0];
            String remainingString = fullCommandLine.substring(fullCommandLine.indexOf(" ") + 1, fullCommandLine.length());
            switch (command){
                case "ADD":
                    hashMap.put(remainingString.split(",")[0], remainingString.split(",")[1]);
                    break;
                case "DELETE":
                    if (remainingString.contains(",")){
                        hashMap.removeValue(remainingString.split(",")[0], remainingString.split(",")[1] );
                    }else{
                        hashMap.removeKey(remainingString);
                    }
                    break;

                case "FIND":
                    if (hashMap.get(remainingString) != null){
                        ArrayList<String> foundNumber = (ArrayList<String>)hashMap.get(remainingString);
                        output.append("Found ").append(foundNumber.size()).append(" phone numbers for ").append(remainingString).append(":");
                        for (String currentNumber : foundNumber){
                            output.append(" ").append(currentNumber);
                        }
                        output.append(" \n");


                    } else {

                        output.append("No contact info found for ").append(splitCommand[1]).append("\n");

                    }
                    break;
            }
            if (commandNumber - i != 1) {
                fullCommandLine = scan.nextLine();
            }
        }
        scan.close();

        System.out.print(output);
        System.exit(0);




    }




}

interface Map <K, V> {
    List<V> get(K key);
    void put(K key, V value);
    void removeKey(K key);
    void removeValue(K key, V value);
    int size();
    boolean isEmpty();
    ArrayList<KeyValueTable<K, V>> entrySet();


}

class KeyValueTable<K, V>{
    K key;
    List<V> value;
    public KeyValueTable (K key, V value){
        this.key = key;
        if (this.value != null){
            this.value.add(value);
        }else {
            this.value = new ArrayList<>();
            this.value.add(value);
        }

    }

    public K getKey() {
        return key;
    }

}

class HashMap<K, V> implements Map<K, V>{

    List<KeyValueTable<K, V>> [] hashTable;
    int capacity;
    int mapSize;


    @Override
    public List<V> get(K key) {
        int index = Math.abs(key.hashCode() % this.capacity);
        for(KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                if (!kv.value.isEmpty()){
                    return kv.value;
                }
            }
        }
        return null;
    }



    @Override
    public void put(K key, V value) {
        // determine the place of our key

        int index = Math.abs(key.hashCode() % this.capacity);
        List<KeyValueTable<K, V>> list = this.hashTable[index];
        boolean keyExists = false;


        for (KeyValueTable<K, V> kv : list) {
            if (kv.getKey().equals(key)) {

                if (!kv.value.contains(value)){
                    kv.value.add(value);
                }
                keyExists = true;
                break;
            }
        }
        if (!keyExists) {
            KeyValueTable<K, V> newKv = new KeyValueTable<>(key, value);
            list.add(newKv);
            this.mapSize++;
        }
    }

    public void removeKey(K key) {
        int index = Math.abs(key.hashCode() % this.capacity);
        Iterator<KeyValueTable<K, V>> iterator = this.hashTable[index].iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(key)) {
                iterator.remove();
                this.mapSize--;
                break;
            }
        }
    }

    // remove value, just remove user telephone number
    @Override
    public void removeValue(K key, V value) {
        int index = Math.abs(key.hashCode() % this.capacity);
        for (KeyValueTable<K, V> kv : this.hashTable[index]) {
            if (kv.getKey().equals(key)) {
                kv.value.remove(value);
                if (kv.value.isEmpty()) {
                    // If the list is empty after removing the value, remove the entire key-value pair
                    this.hashTable[index].remove(kv);
                    this.mapSize--;
                }
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

}


