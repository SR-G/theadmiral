// Small groovy scripts used to download every available images from POSTM-FRANCE
//

def address = "http://www.potsm-france.com/modules/pirates_liste/images/";
def destinationPath = "potsm/"

for (int i = 1 ; i <= 13 ; i++ ) {
    def extension = String.valueOf(i).padLeft(2, "0");
    def currentBaseAddress = address + extension + "/";
	System.out.println("Downloading [" + extension + "]")
	for (int j = 299 ; j <= 410; j++) {
		def cardNumber = String.valueOf(j).padLeft(3, "0");
		for (int k = 1 ; k <= 2; k++) {
			def cardName = cardNumber + "-" + k + ".jpg"
			def cardAddress = currentBaseAddress + cardName
			def cardDestination = destinationPath + extension + "/" + cardName
			if ( ! new File(cardDestination).exists() ) {
				System.out.println("  card [" + cardAddress + "] to [" + cardDestination + "]")
				def destinationFile = new File(destinationPath + extension)
				destinationFile.mkdirs()
				def cardFile = new FileOutputStream(cardDestination)
				def out = new BufferedOutputStream(cardFile)
				try {
					out << new URL(cardAddress).openStream()
				} catch (FileNotFoundException e) {
					new File(cardDestination).delete()
				} catch (IOException e) {
					e.printStackTrace()
					new File(cardDestination).delete()
				} finally {
					out.close()
				}
			}
		}
	}
}
