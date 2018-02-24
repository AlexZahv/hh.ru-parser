package ru.zahv.alex.parser.business.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.zahv.alex.parser.business.service.VacancyService
import java.util.*

@Service
class VacancyServiceImpl : VacancyService {

    private val baseSearchUrl = "https://api.hh.ru/vacancies"

    @Autowired
    lateinit var restTemplate: RestTemplate

    override fun getVacanciesCount(jobTitle: String, areaId: Int): Int {
        return ((getJSON("$baseSearchUrl?text=$jobTitle&area=$areaId") as LinkedHashMap<*, *>)["pages"]) as Int
    }

    override fun getVacanciesPerPage(jobTitle: String, areaId: Int, pageNumber: Int): List<Any> {
        val vacanciesListPage = getJSON("$baseSearchUrl?text=$jobTitle&area=$areaId&page=$pageNumber")
        if (vacanciesListPage != null) {
            return getVacanciesListFromPage(vacanciesListPage) as ArrayList<*>
        }

        return emptyList()
    }

    override fun getVacancy(id: Long): LinkedHashMap<*, *> {
        return getJSON("$baseSearchUrl/$id") as LinkedHashMap<*, *>
    }

    override fun getVacancyKeySkills(vacancy: LinkedHashMap<*, *>): List<String> {
        return ((vacancy)["key_skills"] as ArrayList<*>).map { entry -> (entry as LinkedHashMap<*, *>)["name"] as String }
    }

    fun getJSON(url: String): Any? {
        return restTemplate.getForObject(url, Any::class.java)
    }

    fun getVacanciesListFromPage(html: Any): Any? {
        return (html as LinkedHashMap<*, *>).entries.first { entry -> entry.key == "items" }.value
    }
}