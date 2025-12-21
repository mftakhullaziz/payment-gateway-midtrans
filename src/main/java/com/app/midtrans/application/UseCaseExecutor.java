package com.app.midtrans.application;

public abstract class UseCaseExecutor<I, O> {
    public abstract void execute(I input, O output);
}
