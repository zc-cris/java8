package com.zc.cris.Optional;

import java.util.Optional;

public class NewMan {
    // 将女神属性使用optional 包装一下，创建一个空的option容器
    private Optional<Godness> godness = Optional.empty();

    public NewMan() {

    }

    public NewMan(Optional<Godness> godness) {

        this.godness = godness;
    }

    @Override
    public String toString() {
        return "NewMan{" +
                "godness=" + godness +
                '}';
    }

    public Optional<Godness> getGodness() {

        return godness;
    }

    public void setGodness(Optional<Godness> godness) {
        this.godness = godness;
    }
}
