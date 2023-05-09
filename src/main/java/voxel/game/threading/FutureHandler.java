package voxel.game.threading;

public interface FutureHandler<R> {
    void onSuccess(R result);
    void onFailure(Throwable e);
}
