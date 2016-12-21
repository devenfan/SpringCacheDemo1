package net.sh.spring.demo.entity;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Deven on 2016-12-19.
 */
public class Account implements Serializable {

	private static AtomicInteger ID_SEQ = new AtomicInteger(1);

    private String id;
    private String name;

	public Account () {
		this.id = String.valueOf(ID_SEQ.getAndIncrement());
	}

    public Account(String name) {
		this();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override public String toString() {
		return "Account{" + "id=" + id + ", name='" + name + '\'' + '}';
	}
}
