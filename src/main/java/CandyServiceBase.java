import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Сервис пожирания конфет, требует реализации
 */
public abstract class CandyServiceBase
{

    private static final Logger log = LoggerFactory.getLogger(CandyServiceBase.class);

    private CopyOnWriteArraySet<Integer> eatingCandies = new CopyOnWriteArraySet<>();

    private final CopyOnWriteArrayList<ICandy> iCandies = new CopyOnWriteArrayList<>();
    /**
     * Сервис получает при инициализации массив доступных пожирателей конфет
     * @param candyEaters
     */

    public CandyServiceBase(ICandyEater[] candyEaters) {

        List<ICandyEater> eaters = Arrays.asList(candyEaters);

        Observable.fromIterable(eaters)
                .flatMap(item -> Observable.just(item)
                        .subscribeOn(Schedulers.newThread())
                        .doOnNext(i -> {
                                    Observable.fromIterable(iCandies)
                                            .subscribeOn(Schedulers.computation())
                                            .filter(iCandy -> !eatingCandies.contains(iCandy.getCandyFlavour()) )
                                            .doOnNext(iCandy -> {

                                                eatingCandies.add(iCandy.getCandyFlavour());

                                                try {
                                                    i.eat(iCandy);
                                                    Thread.sleep(500);
                                                } catch (Exception e) {
                                                    log.error("ERROR: ", e);
                                                }

                                                eatingCandies.remove(iCandy.getCandyFlavour());

                                            })
                                            .subscribe();
                                }))
                .retry()
                .subscribe(System.out::println);

    }

    /**
     * Добавить конфету на съедение
     * @param candy
     */

    public abstract void addCandy(ICandy candy);

}