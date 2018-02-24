package ru.zahv.alex.parser.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.zahv.alex.parser.business.service.SearchService

@RestController
@RequestMapping("/search")
class SearchController {

    @Autowired
    lateinit var searchService: SearchService

    @GetMapping("/job")
    fun get(@RequestParam title: String) {
        searchService.searchVacancies(title)
    }
}