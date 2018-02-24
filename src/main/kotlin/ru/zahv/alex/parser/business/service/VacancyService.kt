package ru.zahv.alex.parser.business.service

interface VacancyService {

    fun getVacanciesCount(jobTitle: String, areaId: Int): Int
    fun getVacanciesPerPage(jobTitle: String, areaId: Int, pageNumber: Int): List<Any>
    fun getVacancy(id: Long): LinkedHashMap<*, *>
    fun getVacancyKeySkills(vacancy: java.util.LinkedHashMap<*, *>): List<String>
}