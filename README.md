# NotePad
## 前言
1. 项目使用**Kotlin**进行重写
2. 项目实现基本的**增删改查**
3. 项目实现的拓展功能是：**美化UI，更换笔记背景**
## 项目目录
**1. 代码目录**  
![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/Lib/%E9%A1%B9%E7%9B%AE%E7%9B%AE%E5%BD%95.jpg)
**2. 资源目录**  
![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/Lib/%E8%B5%84%E6%BA%90%E7%9B%AE%E5%BD%95.jpg)
## 首页
**1. 首页UI**  
![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/show/%E4%B8%BB%E7%95%8C%E9%9D%A2.jpg)
![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/show/%E4%BF%9D%E5%AD%98%E5%A4%9A%E6%9D%A1%E6%95%B0%E6%8D%AE.jpg)  
**2. Note展示功能**
    **1. 瀑布流列表写法**  
    在xml中添加  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/MainActivity/main_layout/%E7%AC%94%E8%AE%B0%E5%88%97%E8%A1%A8.jpg)  
    首先获取数据库中的notes，然后全部存放到noteList表格中  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/MainActivity/%E5%88%9D%E5%A7%8B%E5%8C%96Note.jpg)  
    然后创建recyclerview的layoutManager使用瀑布流（StaggeredGridLayoutManager），创建适配器（NoteAdapter），将noteList放入适配器创建列表  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/MainActivity/%E5%88%9D%E5%A7%8B%E5%8C%96%E7%80%91%E5%B8%83%E6%B5%81Recyclerview.jpg)  
    **2. 其中适配器的写法**  
    适配器集成Recyclerview.Adapter，分别实现以下方法  
    **内部类ViewHolder**：展示数据的数据类  
    ![ViewHolder](https://github.com/Github1103/NotePad/blob/master/NotePad_image/NoteAdapter/ViewHolder.jpg)  
    **onCreateViewHolder**：为viewHolder指定样式，对每一个列表项绑定点击事件或者长摁事件。（最为重要的！）  
    ![长摁点击事件](https://github.com/Github1103/NotePad/blob/master/NotePad_image/NoteAdapter/%E9%80%82%E9%85%8D%E5%99%A8%E4%B8%AD%E7%9A%84%E7%82%B9%E5%87%BB%E4%BA%8B%E4%BB%B6%E5%92%8C%E9%95%BF%E6%91%81%E4%BA%8B%E4%BB%B6.jpg)  
    **onBindViewHolder**：为viewHolder绑定list传入的数据  
    ![onbindviewHolder](https://github.com/Github1103/NotePad/blob/master/NotePad_image/NoteAdapter/onBindViewHolder.jpg)     
    **getItemCount**：计数，基本用不到。   
**3. Note删除功能**    
    **1. 这里使用了委托模式**   
    委托模式使用的目的是，当长摁删除事件产生的时候，前端的列表需要对列表进行刷新，但是由于长摁事件在后端，故需要委托模式，委托前端进行刷新动作。  
    **委托模式写法：**  
    在adpter中创建接口  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/NoteAdapter/%E5%A7%94%E6%89%98%E6%A8%A1%E5%BC%8F.jpg)  
    前端实现删除列表的委托动作  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/MainActivity/%E5%88%A0%E9%99%A4%E6%95%B0%E6%8D%AE_%E5%A7%94%E6%89%98%E6%A8%A1%E5%BC%8F.jpg)  
**4. 搜索功能**  
    **1. 搜索功能使用对数据库模糊查询的方式**  
    **(select * from notes where note like ? and title like ?)**  
    ？= key关键字  
    然后对数据库进行搜索，将搜索到的数据存放到searchList中然后调用Recyclerview进行重新展示。  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/MainActivity/%E6%9F%A5%E8%AF%A2%E6%95%B0%E6%8D%AE.jpg)
    **实现展示**  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/show/%E6%90%9C%E7%B4%A2%E5%8A%9F%E8%83%BD%E5%B1%95%E7%A4%BA.jpg)  
**5. 添加功能**
    **1. 点击悬浮摁扭然后进行跳转**   
    xml中添加  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/MainActivity/main_layout/%E6%82%AC%E6%B5%AE%E6%91%81%E6%89%AD.jpg)  
    设置点击事件跳转到EditNote，activity中  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/MainActivity/%E6%8F%92%E5%85%A5%E6%95%B0%E6%8D%AE.jpg) 
## 编辑笔记
**1. 编辑页面UI**  
    进入后会直接锁定到内容输入框中  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/show/%E6%B7%BB%E5%8A%A0%E7%AC%94%E8%AE%B0%E7%95%8C%E9%9D%A2.jpg)  
    右上角换肤摁扭，进入换肤界面  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/show/%E6%8D%A2%E8%83%8C%E6%99%AF%E7%95%8C%E9%9D%A2.jpg)  
    换背景后显示样式  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/show/%E7%BC%96%E8%BE%91%E6%8D%A2%E8%83%8C%E6%99%AF%E5%90%8E.jpg)  
**2. 换肤界面写法**  
    xml中添加     
    抽屉菜单  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/EditNoteActivity/edit_layout/%E6%8A%BD%E5%B1%89%E8%8F%9C%E5%8D%95.jpg)    
    然后添加Recyclerview  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/EditNoteActivity/edit_layout/%E8%83%8C%E6%99%AF%E8%8F%9C%E5%8D%95.jpg)  
    接着写法和前面添加Recyclerview一样。实现imageAdapter，初始化背景列表，添加图片。同样使用了委托类。  
    点击换肤，实现的逻辑是  
    首先是委托类实现  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/EditNoteActivity/%E5%88%9D%E5%A7%8B%E5%8C%96%E5%A7%94%E6%89%98%E6%A8%A1%E5%BC%8F.jpg)  
    然后是adpter中的逻辑。  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/ImageAdapter/%E6%8D%A2%E8%83%8C%E6%99%AF%E9%80%BB%E8%BE%91.jpg)  
**3. 保存功能写法**  
    **1. 点击保存摁扭功能响应事件**  
    **分为两种模式：**  
    0 为添加模式  
    1 为修改模式  
    添加模式使用insert方法插入note数据，修改模式调用update方法修改note数据通过id修改。  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/EditNoteActivity/%E6%8F%92%E5%85%A5%E5%92%8C%E4%BF%AE%E6%94%B9%E4%BF%9D%E5%AD%98%E7%AC%94%E8%AE%B0.jpg)
## 最后Kotlin数据库和ContentProvider写法
**1. 数据库写法**  
    **1. 创建数据库类**  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/Database/%E6%95%B0%E6%8D%AE%E5%BA%93%E7%B1%BB.jpg)  
    **2. 数据库创建语句**  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/Database/%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%9B%E5%BB%BA%E8%AF%AD%E5%8F%A5.jpg)
    **3. 实现OnCreate和OnUpgrade方法**
**2. ContentProvider写法**
    **1. query方法**  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/ContentProvider/ContentProvider_query.jpg)  
    **2. inset方法**  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/ContentProvider/ContentProvider_insert.jpg)  
    **3. delete方法**  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/ContentProvider/ContentProvider_delete.jpg)  
    **4. update方法**  
    ![](https://github.com/Github1103/NotePad/blob/master/NotePad_image/ContentProvider/ContentProvider_update.jpg)  
 
