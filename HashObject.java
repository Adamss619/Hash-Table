/**
 * @author Spencer Adams CS 321 
 *
 * @param <V> - generic object of type V 
 */
public class HashObject<V> {
	
	private V value;
	private int key;


	/**
	 * creates hash object
	 * 
	 * @param hash, key
	 */
	public HashObject(V hash,int key) {
		this.value = hash;
		this.key = key;
	}
	
	/**
	 * @return key of hash object
	 */
	public int getKey() {
		return key;
	}
    
    	/**
	 * @return value of hash object
	 */
	public V getValue() {
		return value;
	}
    
    /**
     * sets new value to hash object
     * 
     * @param newValue
     */
    public void setValue(V newValue){
        this.value = newValue;
            //fix setter
    }
    
    /**
     * sets new key for has object
     * 
     * @param newKey
     */
    public void setKey(int newKey){
        this.key = newKey;
            //fix setter
    }
	
	/**
	 * @param hash
	 * @return true if the key values are equal
	 */
	public boolean equals(HashObject<V> hash) {
		if(hash == null){
			return false;
		}
		if (hash == this) { //somethings wrong with this line causing nullpointer in 4 tests
		  return true;
		} 
		return this.getKey() == hash.getKey() && this.getValue().equals(hash.getValue()); 
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		int hashCode = value.hashCode();
		return hashCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("unchecked")
	public String toString() {
		String str;
		str = getValue().toString() + " " + getKey();
		return str;
	}
	
}
