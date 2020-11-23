package repository

interface IAccelerometerRep {

    fun getJob(jobId: String)
    fun getJobs(page: Int = 0)

}