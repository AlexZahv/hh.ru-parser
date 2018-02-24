package ru.zahv.alex.parser.business.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.zahv.alex.parser.business.service.FileService
import ru.zahv.alex.parser.business.service.SearchService
import ru.zahv.alex.parser.business.service.VacancyService
import java.util.LinkedHashMap
import kotlin.collections.HashMap
import kotlin.collections.set

@Service
class SearchServiceImpl : SearchService {
    private val areaId = 113
    private val firstPageNumber = 0

    @Autowired
    lateinit var vacancyService: VacancyService
    @Autowired
    lateinit var fileService: FileService

    override fun searchVacancies(jobTitle: String) {
        var skillsMap = HashMap<String, Long>()

        val pagesCount = vacancyService.getVacanciesCount(jobTitle, areaId)
        (firstPageNumber until pagesCount)
                .asSequence()
                .map { vacancyService.getVacanciesPerPage(jobTitle, areaId, it) }
                .forEach {
                    it.asSequence()
                            .forEach { vacancy ->
                                skillsMap = inspectVacancy(((vacancy as LinkedHashMap<*, *>)["id"] as String).toLong(), skillsMap)
                            }
                }

        skillsMap = skillsMap.filter { entry -> entry.value > 10 }
                .toList()
                .sortedBy { (_, value) -> value * (-1) }
                .toMap() as HashMap<String, Long>

        fileService.writeStatisticsResult(skillsMap)
    }

    fun inspectVacancy(id: Long, skillsMap: HashMap<String, Long>): HashMap<String, Long> {
        val vacancyJson = vacancyService.getVacancy(id)
        return collectStatistics(vacancyService.getVacancyKeySkills(vacancyJson), skillsMap)
    }

    fun collectStatistics(keySkills: List<String>, skillsMap: HashMap<String, Long>): HashMap<String, Long> {
        keySkills.asSequence().forEach { skill ->
            val key = skill.toLowerCase()
            if (skillsMap.containsKey(key)) {
                skillsMap[key] = skillsMap[key]!! + 1
            } else {
                skillsMap[key] = 1
            }
        }

        return skillsMap
    }
}
