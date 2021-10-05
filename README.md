# BooksDemo
Basic demo of Google Play Books API

Tech stack:
Java, RxJava, Retrofit, Picasso, Timber

This app shows how to use the Books API to perform a simple search of a book volume, which can then be sorted by title/author.
Book volume searches do not need an API key, however, there is code in the app to append your API key to all requests, if needed.

It might be a good idea to have a separate Retrofit instance for calls with an API key, and call without an API key, as the API might not work if you send an API key when one is not required.
