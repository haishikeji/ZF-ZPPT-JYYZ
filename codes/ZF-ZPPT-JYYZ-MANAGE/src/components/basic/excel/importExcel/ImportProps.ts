export interface ImportProps {
  title: string,
  url: string,
  columns: importColumn [],
  template?: importTemplate,
}

//导入模板列名
type importColumn = {
  cnName: string,
  enName: string,
  width: number
}
//导入模板下载配置
type importTemplate = {
  tempFileName: string, //模板名称，本地文件，template目录下文件
  url: string, //导出数据模板url
  params: any //导出数据参数
}
