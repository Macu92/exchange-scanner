package org.cryptoscanner.exchangescanner.service;

import org.cryptoscanner.exchangescanner.job.MarketJob;
import org.cryptoscanner.exchangescanner.model.Market;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

@Component
public class MarketJobService {
    private List<MarketJob> marketJobs;

    public MarketJobService() {
        this.marketJobs = new LinkedList<>();
    }

    public MarketJobService(List<MarketJob> marketJobs) {
        this.marketJobs = marketJobs;
    }

    public int addMarketJob(Market market) throws Exception {
        marketJobs.add(new MarketJob(market));
        return marketJobs.size()-1;
    }

    public void deleteMarketJob(Market market){
        marketJobs.remove(market);
    }

    public List<MarketJob> getMarketJobs(){
        return marketJobs;
    }

    public void startJobs(){
        final Timer timer = new Timer(true);
        marketJobs.forEach(job -> {
            if(job.scheduledExecutionTime()==0) {
                timer.scheduleAtFixedRate(job, 0, 10 * 1000);
            }
        });
    }
}
