package com.vit.mychat.remote.common;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RxFirebase {

    public static <T> Observable<T> getValue(DatabaseReference ref, Class<T> clazz) {
        return Observable.create(emitter ->
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        emitter.onNext(dataSnapshot.getValue(clazz));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    public static <T> Single<T> getValueSingle(DatabaseReference ref, Class<T> clazz) {
        return Single.create(emitter ->
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        emitter.onSuccess(dataSnapshot.getValue(clazz));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    public static <T> Observable<List<T>> getList(DatabaseReference ref, Class<T> clazz) {
        return Observable.create(emitter ->
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<T> list = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            list.add(data.getValue(clazz));
                        }
                        emitter.onNext(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    public static Observable<List<String>> dataChangeKeyList(DatabaseReference ref, String value) {
        return Observable.create(emitter ->
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> list = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (data.getValue(String.class).contains(value)) {
                                list.add(data.getKey());
                            }
                        }
                        emitter.onNext(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    public static Observable<DataSnapshot> getDataSnapshot(DatabaseReference ref) {
        return Observable.create(emitter ->
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        emitter.onNext(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    public static <T> Completable setValue(DatabaseReference ref, T t) {
        return Completable.create(emitter ->
                ref.setValue(t, (databaseError, databaseReference) -> {
                    if (!emitter.isDisposed()) {
                        if (null != databaseError) {
                            emitter.onError(databaseError.toException());
                        } else {
                            emitter.onComplete();
                        }
                    }
                }));
    }

    public static Completable updateChildren(DatabaseReference ref, Map map) {
        return Completable.create(emitter ->
                ref.updateChildren(map, (databaseError, databaseReference) -> {
                    if (!emitter.isDisposed()) {
                        if (null != databaseError) {
                            emitter.onError(databaseError.toException());
                        } else {
                            emitter.onComplete();
                        }
                    }
                }));
    }
}
