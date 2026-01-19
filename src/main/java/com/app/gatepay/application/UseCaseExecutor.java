package com.app.gatepay.application;

public abstract class UseCaseExecutor<I, O> {
    public abstract void execute(I input, O output);
}
