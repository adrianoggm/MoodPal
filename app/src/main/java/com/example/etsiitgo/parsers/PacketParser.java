/**
 * @author Alejandro
 */

package com.example.etsiitgo.parsers;

public interface PacketParser<T> {
    T parse(String packet);
}
