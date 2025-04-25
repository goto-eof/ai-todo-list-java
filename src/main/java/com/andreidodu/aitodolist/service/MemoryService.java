package com.andreidodu.aitodolist.service;

import java.util.List;

public interface MemoryService {

    String store(String messgae);

    List<String> getAll();

    String delete(String message);

}
