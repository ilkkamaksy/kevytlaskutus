package kevytlaskutus.dao;

import java.sql.*;
import java.util.List;

public interface ProductDao<T, K, S> {
    public boolean create(T object) throws SQLException;
    public T getItemById(K key) throws SQLException;
    public boolean update(int id, T object) throws SQLException;
    public boolean delete(K key) throws SQLException;
    public List<T> getItems() throws SQLException;
}
