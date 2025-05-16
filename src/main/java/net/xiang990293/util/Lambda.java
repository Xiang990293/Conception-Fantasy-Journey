package net.xiang990293.util;

import java.util.function.Consumer;

public interface Lambda<T, U> {
    T func(U... params);
}
