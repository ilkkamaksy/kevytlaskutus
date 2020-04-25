package kevytlaskutus.dao;

import java.sql.*;
import java.util.List;

public interface InvoiceDao<T, K, S> {
    public K create(T object) throws SQLException;
    public T getItemById(K key) throws SQLException;
    public boolean update(int id, T object) throws SQLException;
    public boolean delete(K key) throws SQLException;
    public List<T> getItems(K key) throws SQLException;
    public K getInvoiceCount() throws SQLException;
}
