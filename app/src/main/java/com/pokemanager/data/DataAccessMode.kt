package com.pokemanager.data

sealed class DataAccessMode {

    /*
    * Download everything at the beginning
    * In network (and db) only a class per entity with everything
    * And in Domain a class for list on other with all the detail
    * */
    object DownloadAll: DataAccessMode()

    /*
    * Request what is needed when is needed then is persisted
    * In network and Domain a class per entity for list on other with all the detail
    * And in db only a class per entity with everything
    * */
    object RequestAndDownload: DataAccessMode()

    /*
    * Request what is needed when is needed without being persisted
    * In network and Domain a class per entity for list on other with all the detail
    * Here there's no db entity
    * */
    object OnlyRequest: DataAccessMode()

}
