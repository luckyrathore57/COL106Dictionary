import java.util.LinkedList;

import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;

public class COL106Dictionary<K, V> {

    private LinkedList<DictionaryEntry<K, V>> dict;

    public LinkedList<HashTableEntry<K, V>>[] hashTable;

    
    @SuppressWarnings("unchecked")
    COL106Dictionary(int hashTableSize) {
        dict = new LinkedList<DictionaryEntry<K, V>>();
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        }

    private int size=0;

    private int searchInLL(int bi,K key) {
        if(key==null){
            return -1;
        }
        int index = -1;
        if(hashTable[bi]!=null) {
            int lens = this.hashTable[bi].size();
            for (int i = 0; i < lens; i++) {
                if (key.equals(hashTable[bi].get(i).key)) {
                    index = index + i;
                    return index + 1;
                }
            }
        }
        return index;
    }




    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {

        int bi = this.hash(key);
        int inde=this.searchInLL(bi,key);
//        System.out.println(bi + "     "  +inde);
//        int mi = this.searchInMLL(key);
        if(key==null){
            throw new NullKeyException();
        }
        if(inde !=-1){
            throw new KeyAlreadyExistException();
        }
        DictionaryEntry<K,V> kkvv=new DictionaryEntry<>(key,value);
        if(hashTable[bi] == null){
            hashTable[bi]=new LinkedList<HashTableEntry<K,V>>();
            hashTable[bi].addFirst(new HashTableEntry<>(key, kkvv));
        }
        else {
            this.hashTable[bi].add(new HashTableEntry<>(key, kkvv));
        }
           this.dict.add(kkvv);
        size=size+1;
    }


    public V delete(K key) throws NullKeyException, KeyNotFoundException{

        int bi = this.hash(key);
        int inde=this.searchInLL(bi,key);
//        int mi=this.searchInMLL(key);
        if (inde == -1) {
            throw new KeyNotFoundException();
        }
        if(key==null){
            throw new NullKeyException();
        }

//        this.dict.remove(mi);
        V vvv=this.hashTable[bi].get(inde).dictEntry.value;
        this.dict.remove(this.hashTable[bi].get(inde).dictEntry);
        hashTable[bi].remove(inde);
        size--;

        return vvv;
    }

    public V update(K key, V value) throws NullKeyException, KeyNotFoundException{

        if (key == null) {
            throw new NullKeyException();
        }
        int bi = this.hash(key);
        int inde=this.searchInLL(bi,key);
//        int mi = this.searchInMLL(key);
        if (inde == -1) {
            throw new KeyNotFoundException();
        }
        V temp=this.hashTable[bi].get(inde).dictEntry.value;
        this.hashTable[bi].get(inde).dictEntry.value=value;
//        this.dict.get(mi).value=value;


        return temp;
    }

    public V get(K key) throws NullKeyException, KeyNotFoundException {
        if (key == null) {
            throw new NullKeyException();
        }

        int bi = this.hash(key);
        int inde=this.searchInLL(bi,key);
        if (inde == -1) {
            throw new KeyNotFoundException();
        }
        V vv=this.hashTable[bi].get(inde).dictEntry.value;

        return vv;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {

        K[] arrKey=(K[]) Array.newInstance(cls,this.size());
        int ii=0;
        for(DictionaryEntry<K, V> i : this.dict){
            arrKey[ii]=i.key;
            ii++;
        }

        return arrKey;
    }

    @SuppressWarnings("unchecked")
    public V[] values(Class<V> cls) {

        V[] arrValue=(V[]) Array.newInstance(cls,this.size());
        int ii=0;
        for(DictionaryEntry<K, V> i : this.dict){
            arrValue[ii]=i.value;
            ii++;
        }
        return arrValue;
    }

    public int hash(K key) {
        if(key==null){
            return -1;
        }
        int p = 131;
        int m = hashTable.length;
        int ans = 0;
        int ppp = 1;
        String ss= key.toString();
        for(int i = 0; i < ss.length(); i++) {
            ans = (ans + (ss.charAt(i)  + 1) * ppp) % m;
            ppp = (ppp*p)% m;
        }
        return (int)ans;
    }
}
