package ru.zahv.alex.parser.business.service

interface FileService {
    fun writeStatisticsResult(skillsMap: HashMap<String, Long>)
}