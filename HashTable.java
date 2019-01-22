import java.util.NoSuchElementException;

/**
 * @author spenceradams 321
 *
 * @param <V> - generic object of type V
 */
public class HashTable<V> {
	private int size;
	private int dups;
	private int insertions;
	private int[] frequency = new int[100000];
	private int capacity;
	private float loadFactor;
	private OpenAddressType hashType;// openAddress
	private int numProbes; // numProbes
	private int DEFAULT_CAPACITY = 13;
	private float DEFAULT_LOAD_FACTOR = (float) .75;
	private OpenAddressType DEFAULT_TYPE = OpenAddressType.linear;
	private int maxSize;
	private HashObject<V>[] hashTable = new HashObject[100000];

	/**
	 * @param indicator
	 * @param size
	 */
	public HashTable() {
		capacity = DEFAULT_CAPACITY;
		loadFactor = DEFAULT_LOAD_FACTOR;
		hashType = DEFAULT_TYPE;
		// hashTable = new HashObject[getMaxSize()];
	}

	/**
	 * @param capacity - prime number with another prime 2 values less
	 */
	public HashTable(int capacity) {
		this.capacity = capacity;
		loadFactor = DEFAULT_LOAD_FACTOR;
		hashType = DEFAULT_TYPE;
		// hashTable = new HashObject[maxSize];
	}

	/**
	 * @param capacity - prime number with another prime 2 values less
	 * @param loadF - percent that the hashtable can be filled
	 */
	public HashTable(int capacity, float loadF) {
		this.capacity = capacity;
		loadFactor = loadF;
		hashType = DEFAULT_TYPE;
		// hashTable = new HashObject[maxSize];
	}

	/**
	 * @param capacity - prime number with another prime 2 values less
	 * @param loadF - percent that the hash table can be filled
	 * @param type - type of hashing
	 */
	public HashTable(int capacity, float loadF, OpenAddressType type) {
		this.capacity = capacity;
		loadFactor = loadF;
		hashType = type;
		// hashTable = new HashObject[maxSize];
		// if no extra credit throw exception
	}
	// should call gethash
	// m is capacity

	/**
	 * hashes and puts inserts into hash if no duplicate exists inside hash
	 * already. Increments probe count as long as value found is not a duplicate.
	 * 
	 * @param value - object being hashed
	 * @param key - key used for hashing object
	 */
	public void put(V value, int key) {
		maxSize = (int) (loadFactor * capacity);
		// System.out.println(maxSize);
		if (size + 1 > maxSize) { // if a put call would put size over capacity
			throw new IllegalStateException();
		}
		HashObject<V> hash = new HashObject<V>(value, key); // create object to
															// put

		if (contains(value, key) == true) { // checks for doubles
			int probe = 0;
			int hashCheck = getHash(key, probe); // checks for duplicate and
													// counts probes required
													// till dupe found
			while (hashTable[hashCheck].equals(hash) != true) {
				if (probe != capacity) {
					probe++;
				}
				hashCheck = getHash(key, probe);
			}
			numProbes += probe;
			dups++;

		} else {
			if (hashTable[getHash(key, 0)] == null) {// found empty slot
				hashTable[getHash(key, 0)] = hash;
				size++;
				// numProbes++;
				insertions++;
			} else {
				int probe = 1;
				int hashCheck = getHash(key, probe);
				while (hashTable[hashCheck] != null) {
					// not empty and inserting non-duplicate using linear search
					if (probe != capacity) {
						probe++;
					} // searches for empty hash to place value in
					hashCheck = getHash(key, probe);
				}
				numProbes += probe;
				size++;
				hashTable[hashCheck] = hash;
				insertions++;
			}
		}
	}

	/**
	 * @param a - object to remove
	 * @param b - key to the object being removed
	 * @return hash object removed from hash table, if not found throws
	 *         exception
	 */
	public V remove(V a, int b) {
		V removed = null;
		if (contains(a, b) != true) {
			throw new NoSuchElementException();
		}
		HashObject<V> hash = new HashObject<V>(a, b);
		for (int i = 0; i < capacity; i++) {
			if (hash.equals(hashTable[i])) {
				removed = hashTable[i].getValue();
				hashTable[i] = null;
				frequency[i] = 0;
				size--;
			}
		}
		return removed;
	}

	/**
	 * @param a - object being searched for
	 * @param b - key of object being searched for
	 * @return true if object found false if not
	 */
	public boolean contains(V a, int b) {
		boolean check = false;
		HashObject<V> hash = new HashObject<V>(a, b);
		for (int i = 0; i < hashTable.length; i++) {
			if (hash.equals(hashTable[i])) {
				frequency[i]++;
				check = true;
			}
		}
		return check;
	}

	/**
	 * clears all values in hash table
	 */
	public void clear() {
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = null;
			frequency[i] = 0;
		}
		size = 0;
		numProbes = 0;
	}

	/**
	 * @param key - key value that is going to be hashed
	 * @param freq - the number of attempts made to insert said value
	 * @return hashed value
	 */
	public int getHash(int key, int freq) {
		if (hashType == OpenAddressType.linear) {
			return hash1(key, freq); // if learn use hash1 which is linear
		} else if (hashType == OpenAddressType.double_hash) {
			return hash2(key, freq);
		} else {
			return hash3(key, freq);
		}
		// decides which hash to use linear or doubleHasing calls that method
		// and returns hash

	}

	/**
	 * @param key - key for a specific value
	 * @param i - number of attempts made to insert said value
	 * @return hashed linear value
	 */
	public int hash1(int key, int i) {
		int localHash = ((key % capacity) + i) % capacity; // i being the number
		return Math.abs(localHash);						// of attempts made
															// to insert a given
															// values
		}

	/**
	 * @param key - key for a specific value
	 * @param i - number of attempts made to insert said value
	 * @return hashed double value
	 */
	public int hash2(int key, int i) {
		int doubleSlot = ((key % capacity) + i * (1 + (key % (capacity - 2)))) % capacity;
		return Math.abs(doubleSlot);
		// hash function for double hashing same parameters c1=c2=.5
	}

	/**
	 * @param key - key for a specific value
	 * @param i - number of attempts made to insert said value
	 * @return quadratic hashed value
	 */
	public int hash3(int key, int i) {
		int doubleSlot = (int) Math.round(((key % capacity) * (key) + .5 * i + .5 * Math.pow(i, 2)) % capacity);
		return Math.abs(doubleSlot);
	}

	/**
	 * @return capacity of hash table
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return load factor for hash table
	 */
	public float getLoadFactor() {
		return loadFactor;
	}

	/**
	 * @return type of hashing
	 */
	public OpenAddressType getType() {
		return hashType;
	}

	/**
	 * @return number of insertions
	 */
	public int getInsertions() {
		return insertions;
	}

	/**
	 * @return current size of hash table
	 */
	public int size() {
		return size;
	}

	/**
	 * @return if hash table is empty
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * @return number of probes
	 */
	public int getNumProbes() {
		return numProbes;
	}

	/**
	 * @return max size of hash table
	 */
	public int getMaxSize() {
		return (int) (loadFactor * capacity);
	}

	/**
	 * @param a - value looking for frequency of
	 * @param b - key of the value
	 * @return frequency of the value being inserted into the hash table
	 */
	public int getFrequency(V a, int b) {
		int freq = 0;
		boolean found = false;
		HashObject<V> hash = new HashObject<V>(a, b);
		for (int i = 0; i < capacity; i++) {
			if (hash.equals(hashTable[i])) {
				freq = frequency[i];
				found = true;
			}
		}
		if (found != true) {
			freq = -1;
		}
		return freq;
	}

	/**
	 * @return number of duplicate value entry attempts
	 */
	public int getNumDuplicates() {
		return dups;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer string = new StringBuffer("\n");
		for (int i = 0; i < capacity; i++) {
			if (hashTable[i] != null) {
				string.append("table[" + i + "]: ");
				string.append(hashTable[i].getValue().toString() + " " + frequency[i]);
				string.append("\n");
			}
		}
		return string.toString();
	}
}
