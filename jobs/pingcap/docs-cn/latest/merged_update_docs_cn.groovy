// REF: https://<your-jenkins-server>/plugin/job-dsl/api-viewer/index.html
// For trunk and latest release branches.
pipelineJob('pingcap/docs-cn/merged_update_docs_cn') {
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
        githubProjectUrl("https://github.com/pingcap/docs-cn")
    }

    definition {
        cpsScm {
            lightweight(true)
            scriptPath("pipelines/pingcap/docs-cn/latest/merged_update_docs_cn.groovy")
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
