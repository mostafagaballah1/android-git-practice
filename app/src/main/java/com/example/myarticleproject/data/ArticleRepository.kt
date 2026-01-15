package com.example.myarticleproject.data

import com.example.myarticleproject.model.Article

class ArticleRepository {

    suspend fun getArticles(getArticles: Boolean = true): Result<List<Article>> {
        return if (getArticles) {
            Result.success(
                listOf<Article>(
                    Article(
                        1,
                        "Welcome to First Title",
                        "Description 1",
                        "1/1/2023"
                    ),
                    Article(
                        2,
                        "Welcome to Second Title",
                        "Description 2",
                        "2/2/2023"
                    ),
                    Article(
                        3,
                        "Welcome to Third Title",
                        "Description 3",
                        "3/3/2023"
                    ),
                    Article(
                        4,
                        "Welcome to Fourth Title",
                        "Description 4",
                        "4/4/2023"
                    )
                )
            )
        } else {
            Result.failure(Exception("can't load the articles"))
        }
    }

}