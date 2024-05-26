package storeage;

public interface DataStoreInterface {
    public void writeFile(Object obj);
    public Object readFile() throws ClassNotFoundException;
}
