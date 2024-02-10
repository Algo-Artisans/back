package com.example.AA.repository;

import com.example.AA.dto.PortfolioResDto;
import com.example.AA.dto.PortfolioSearchCondition;
import com.example.AA.dto.QPortfolioResDto;
import com.example.AA.entity.*;
import com.example.AA.entity.enumtype.HairName;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;


// 검색 저장소

@Slf4j
@Repository
public class PortfolioSearchRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PortfolioSearchRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<PortfolioResDto> searchHairname(String hairName) {
        log.info("searchHairname1");
        QPortfolio portfolio = QPortfolio.portfolio;
        QPortfolioHairStyle portfolioHairStyle = QPortfolioHairStyle.portfolioHairStyle;
        QWorkImage workImage = QWorkImage.workImage;
        QHairStyle hairStyle1 = new QHairStyle("hairStyle1");
        QHairStyle hairStyle2 = new QHairStyle("hairStyle2");
        QHairStyle hairStyle3 = new QHairStyle("hairStyle3");
        log.info("searchHairname2");
        Predicate predicate = null;
        if (!StringUtils.isEmpty(hairName)) {
            HairName convertedHairName = HairName.valueOf(hairName);
            predicate = portfolioHairStyle.hairStyle.hairName.eq(convertedHairName);
        }
        log.info(predicate.toString());
        return queryFactory
                .selectDistinct(new QPortfolioResDto(
                        portfolio.user,
                        portfolio,
                        workImage,
                        hairStyle1,
                        hairStyle2,
                        hairStyle3
                ))
                .from(portfolio)
                .leftJoin(portfolio.portfolioHairStyles, portfolioHairStyle)
                .leftJoin(portfolio.workImages, workImage)
                .leftJoin(portfolioHairStyle.hairStyle, hairStyle1).on(hairStyle1.hairStyleId.eq(portfolioHairStyle.hairStyle.hairStyleId))
                .leftJoin(portfolioHairStyle.hairStyle, hairStyle2).on(hairStyle2.hairStyleId.ne(hairStyle1.hairStyleId))
                .leftJoin(portfolioHairStyle.hairStyle, hairStyle3).on(hairStyle3.hairStyleId.ne(hairStyle1.hairStyleId), hairStyle3.hairStyleId.ne(hairStyle2.hairStyleId))
                .where(predicate)
                .fetch();
    }

}




