# Running Big Data Infrastructure in the Cloud - An Outline

* Network definition/layout
  * AWS VPC
  * Cluster placement groups (may not be able to add incrementally)
  * Time coordination - NTP
  * DNS resolution - important for dynamic machines
  * In AWS you can edit security group permissions, but you CANNOT add SGs to a running instance!
* Provisioning Machines
  * Hand-roll AMI (machine image)
  * Use Chef/Puppet/etc. to provision machines
  * Use Packer (packer.io) to automatically create images (via shell, Chef, etc.)
* Hardware selection (instance type, etc.)
  * Noisy neighbor problems
  * http://blog.cloudera.com/blog/2014/02/best-practices-for-deploying-cloudera-enterprise-on-amazon-web-services/
  * Don't use EBS for high-throughput disk applications
  * Either provision a large root volume, or additional drive mounts for logs (so you don't fill up root)
* Monitoring
  * Hopefully you're using a configuration tool (like Cloudera Manager or MapR Control System); definitely set up alerting from that tool
  * CloudWatch custom metrics are $0.50/month ea. (first 10 are free) and can be written from the output of any CLI program; can alert from those as well
* Temporary capacity
  * Elastic Mapreduce (EMR) is usually used for temporary capacity
  * EMR supports Apache and MapR distros
  * Depending on your setup, clusters may or may not be wire-compatible; snapshots or especially raw dumps of data to/from S3 may be good intermediary
  * Can launch clusters with API and CLI tools
