package com.wlmtest.forecasttestjava.repository.openweathermap

import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Developed by skydoves on 2018-08-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class OpenWeatherMapServiceTest : ApiAbstract<OpenWeatherMapService>() {

  private lateinit var service: OpenWeatherMapService

  @Before
  fun initService() {
    this.service = createService(OpenWeatherMapService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun findCurrentWeatherDataByCityName() {
    enqueueResponse("/tmdb_movie.json")
//    val response = LiveDataTestUtil.getValue(service.findCurrentWeatherDataByCityName("",""))
//    assertThat(response.body?.results?.get(0)?.id, `is`(164558))
//    assertThat(response.body?.total_results, `is`(61))
//    assertThat(response.body?.total_pages, `is`(4))
  }

}
