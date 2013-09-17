jabref-2.9.2
============

Manutenção e Evolução de software sobre o código fonte do JabRef versão 2.9.2, com base no  material de Rajlich


Abrindo o projeto no Eclipse
================================================

1 - Para as alterações realizadas neste repositório, a versão do <b>Eclipse</b> a ser utilizada será a 4.3 
(<b>Eclipse Kepler</b>), que pode ser baixada para os sistemas operacionais Windows, Linux e Mac neste link:

http://www.eclipse.org/downloads/

2 - Também é necessário instalar o cliente de controle de versão <b>GIT</b>, que pode ser baixado neste endereço:

http://git-scm.com/

3 - Tendo instalado o Eclipse Kepler e o GIT, abra o Eclipse, na perspectiva "<i>Git Repository Exploring</i>". Clique no 
botão "<i>Clone a Git Repository and add the clone to this view</i>". 

<img src="http://docs.joomla.org/images/b/bd/Git-coders-tutorial-20121009-01.png" />

No campo URI, cole o endereço deste repositório:

https://github.com/sfohart/jabref-2.9.2.git

Clicando em "next", você poderá ver todos os branches disponíveis neste repositório. Neste caso, os seguintes branches estarão 
disponíveis:

<ul>
	<li>master</li>
	<li>phase-01</li>
</ul>

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/branch-selection.PNG" />

Clicando mais uma vez em "next", você poderá configurar em qual diretório o Eclipse clonará o repositório git.

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/local-destination.PNG" />

É recomendável que você deixe a configuração como está. Clique em "Finish" e o Eclipse fará o download dos 
arquivos deste repositório para o seu computador.

4 - Crie um branch local de nome "phase-01" 

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/criando-branch-local.png" />

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/criando-branch-local-phase-01.png" />

5 - Expanda o diretório de trabalho, clique com o botão direito do mouse na pasta "<b>jabref-2.9.2</b>" e selecione
a opção <b>Import Projects...</b>. Clique em "next" e, depois, em "finish"

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/import-projects.png" />

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/import-projects-01.png" />

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/import-projects-02.png" />

6 - Abra a visão do <b>Ant</b>, clicando em Window > Show View > Ant

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/configurando-ant.png" />

Clique no botão <b>Add buildfiles</b>

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/configurando-ant-01.png" />

Selecione o arquivo <b>build.xml</b> e clique no botão <b>OK</b>

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/configurando-ant-02.png" />

Para compilar e executar o projeto, execute a task <b>run</b> deste build file.

<img src="https://raw.github.com/sfohart/jabref-2.9.2/master/doc/configurando-ant-03.PNG" />





