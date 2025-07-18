// REF: https://<your-jenkins-server>/plugin/job-dsl/api-viewer/index.html
// For release-9.0-beta.M branches.
pipelineJob('pingcap/tidb/release-9.0-beta/pull_build') {
    logRotator {
        daysToKeep(30)
    }
    parameters {
        // Ref: https://docs.prow.k8s.io/docs/jobs/#job-environment-variables
        stringParam("BUILD_ID")
        stringParam("PROW_JOB_ID")
        stringParam("JOB_SPEC")
    }
    properties {
        // priority(0) // 0 fast than 1
        githubProjectUrl("https://github.com/pingcap/tidb")
    }

    definition {
        cpsScm {
            lightweight(true)
            scriptPath("pipelines/pingcap/tidb/release-9.0-beta/pull_build.groovy")
            scm {
                git{
                    remote {
                        url('https://github.com/PingCAP-QE/ci.git')
                    }
                    branch('main')
                    extensions {
                        cloneOptions {
                            depth(1)
                            shallow(true)
                            timeout(5)
                        }
                    }
                }
            }
        }
    }
}
