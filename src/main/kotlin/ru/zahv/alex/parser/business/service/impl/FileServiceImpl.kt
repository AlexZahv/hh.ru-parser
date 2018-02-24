package ru.zahv.alex.parser.business.service.impl

import org.springframework.stereotype.Service
import ru.zahv.alex.parser.business.service.FileService
import java.nio.file.Files
import java.nio.file.Paths

@Service
class FileServiceImpl : FileService {
    private val path = "data/"
    private val resultFileName = "result.txt"

    override fun writeStatisticsResult(skillsMap: HashMap<String, Long>) {
        Files.createDirectories(Paths.get(path))
        val filePath = Paths.get(path + resultFileName)

        Files.deleteIfExists(filePath)
        Files.createFile(filePath)
        val stringBuffer = StringBuffer()
        skillsMap.forEach { skill ->
            stringBuffer.append(skill.key + " : " + skill.value + "\n")
        }

        Files.write(filePath, (stringBuffer.toString()).toByteArray())
    }
}