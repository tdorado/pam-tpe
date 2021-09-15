package com.td.wallendar.home.events.ui;

import com.td.wallendar.models.Event;
import com.td.wallendar.repositories.interfaces.EventsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class EventsPresenter {

    private final EventsRepository eventsRepository;
    private final SchedulerProvider schedulerProvider = new AndroidSchedulerProvider();
    private final CompositeDisposable disposable = new CompositeDisposable();

    private WeakReference<EventsView> eventsView;

    public EventsPresenter(final WeakReference<EventsView> eventsView,
                           final EventsRepository eventsRepository) {
        this.eventsView = eventsView;
        this.eventsRepository = eventsRepository;
    }

    public void listEvents(final long userId) {
        if (eventsRepository != null) {
            disposable.add(eventsRepository.getEventsByUserId(userId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(this::onEventsReceived, this::onEventsError));
        }
    }

    private void onEventsReceived(final List<Event> events) {
        if (eventsView != null) {
            eventsView.get().showEvents(events);
        }
    }

    private void onEventsError(Throwable throwable) {
        // TODO
    }
}
