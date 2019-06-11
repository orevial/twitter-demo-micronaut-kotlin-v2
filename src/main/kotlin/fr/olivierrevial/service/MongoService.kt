package fr.olivierrevial.service

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success
import io.reactivex.Flowable
import io.reactivex.Single
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import com.mongodb.BasicDBObject



@Singleton
class MongoService(private val mongo: MongoClient) {
    companion object {
        val LOG = LoggerFactory.getLogger(MongoService::class.java)
        const val TWEETS_COLLECTION = "tweets"
    }

    fun list(total: Int): Flowable<Tweet> =
            Flowable.fromPublisher<Tweet>(getCollection().find()
                    .sort(BasicDBObject("createdAt", -1))
                    .limit(total))

    fun save(tweets: List<Tweet>): Single<String> {
        LOG.info("""Saving ${tweets.size} tweets in Mongo...""")
        return Single.fromPublisher<Success>(getCollection().insertMany(tweets))
                .map { """Successfuly saved ${tweets.size} tweets to Mongo...""" }
    }

    fun deleteAll(): Single<String> {
        LOG.info("""Deleting entire  $TWEETS_COLLECTION collection from Mongo...""")
        return Single.fromPublisher<Success>(getCollection().drop())
                .map { "Successfuly deleted entire $TWEETS_COLLECTION from Mongo..." }
    }

    private fun getCollection(): MongoCollection<Tweet> =
        mongo
            .getDatabase("micronaut")
            .getCollection<Tweet>(TWEETS_COLLECTION, Tweet::class.java)
}